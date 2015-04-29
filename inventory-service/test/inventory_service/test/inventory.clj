(ns inventory-service.test.inventory
  (:use   clojure.test
          inventory-service.logic.inventory))

(def not-nil? (complement nil?))

(deftest test-logic
  (testing "fetch inventory"
    (let [ expected "abcd"
           result (fetchInventory expected)]
      (is (not-nil? result))
      (is (= expected (result :id))))))

(deftest test-fetch-inventory
  (let [expectedCount 200]
    (with-redefs [inventory-service.logic.inventory/fetchCount (fn[] expectedCount)]
      (testing "should fetch count"
        (let [ expected "abcde"
               result (fetchInventory expected)]
          (is (= expectedCount (result :count)))
          ))))
  )