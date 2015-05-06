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

(facts "settings"
  (with-state-changes [(before :facts (resetSettings))]
    (fact "should be returned from 'getSettings'"
      (let [result (getSettings)]
        (:errorRate result) => 0
        (:minLatency result) => 100
        (:maxLatency result) => 500
        ))
    (fact "should allow updates"
      (setSettings 0.05 500 500)
      (let [result (getSettings)]
        (:errorRate result) => 0.05
        (:minLatency result) => 500
        (:maxLatency result) => 500
        ))
    ))


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