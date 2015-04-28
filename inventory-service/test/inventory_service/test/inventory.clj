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