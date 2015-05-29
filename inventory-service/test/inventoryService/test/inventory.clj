(ns inventoryService.test.inventory
  (:use   clojure.test
          midje.sweet
          inventoryService.logic.inventory))

(def not-nil? (complement nil?))

(facts "fetch inventory"
  (with-state-changes [(before :facts (resetSettings))]
    (fact "should return data"
      (fetchInventory anything) => not-nil?
      (provided
        (roll-dice anything) => false
        (random-delay) => 0)
    )
    (fact "should return count"
        (:count (fetchInventory "1234")) => 300
        (provided
          (roll-dice anything) => false
          (random-delay) => 0
          (fetchCount) => 300)
        )))

(facts "settings"
  (with-state-changes [(before :facts (resetSettings))]
    (fact "should be returned from 'getSettings'"
      (let [result (getSettings)]
        (:errorRate result) => 0
        (:minLatency result) => 100
        (:maxLatency result) => 500
        (:errorLatency result) => 2000
        ))
    (fact "should allow updates"
      (setSettings 0.05 500 500 3000)
      (let [result (getSettings)]
        (:errorRate result) => 0.05
        (:minLatency result) => 500
        (:maxLatency result) => 500
        (:errorLatency result) => 3000
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
      (roll-dice anything) => false
      (random-delay) => 0)
    )
  (fact "should error after max delay"
    (let [expectedDelay 200]
      (setSettings 1 0 0 expectedDelay)
      (fetchInventory anything) => irrelevant
      (provided
        (roll-dice anything) => true
        (exec-after-delay expectedDelay anything) => irrelevant))
      )
    )