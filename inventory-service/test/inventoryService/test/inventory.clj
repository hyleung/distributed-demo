(ns inventoryService.test.inventory
  (:use   clojure.test
          midje.sweet
          inventoryService.logic.inventory))

(def not-nil? (complement nil?))

(deftest test-logic
  (testing "fetch inventory"
    (let [ expected "abcd"
           result (fetchInventory expected)]
      (is (not-nil? result))
      (is (= expected (result :id))))))

(deftest test-fetch-inventory
  (let [expectedCount 300]
    (with-redefs [fetchCount (fn[] expectedCount)]
      (testing "should fetch count"
        (let [ expected "abcde"
               result (fetchInventory expected)]
          (is (= expectedCount (result :count)))
          ))))
  )

(deftest test-settings
  (testing "should initialize settings to defaults"
    (let [result (getSettings)]
      (is (= 0 (:errorRate result)))
      (is (= 100 (:minLatency result)))
      (is (= 500 (:maxLatency result)))
      ))
  (testing "should set settings"
    (setSettings 0.05 500 500)
    (let [setting (getSettings)]
      (is (= 0.05 (:errorRate setting)))
      (is (= 500 (:minLatency setting)))
      (is (= 500 (:maxLatency setting)))
      )))

(facts "fetchInventory"
  (fact "should return random error"
    (fetchInventory anything) => (throws Exception)
    (provided
      (roll-dice anything) => true)
    )
  (fact "should return something"
    (fetchInventory anything) => not-nil? 
    (provided
      (roll-dice anything) => false)
    ))