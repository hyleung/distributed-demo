(ns inventoryService.logic.inventory)

(defrecord ServiceSettings [errorRate minLatency maxLatency])

(def service-settings (atom (ServiceSettings. 0 100 500)))

;;roll the dice for an error
(defn roll-dice[errorRate]
  (< (rand) errorRate))

(defn random-delay[]
  (first
    (shuffle (range (:minLatency @service-settings) (:maxLatency @service-settings) 10)))
  )

(defn fetchCount[]
  (first (shuffle (range 0 500 10))))

(defn fetchInventory[productId]
  (Thread/sleep (random-delay))
  (if (roll-dice (:errorRate @service-settings))
    (throw (Exception. "error"))
    {:id productId :inStock true :count (fetchCount )}
    )
  )

(defn setSettings[errorRate minLatency maxLatency]
  (do
    (swap! service-settings (fn[x] (ServiceSettings. errorRate minLatency maxLatency))) @service-settings
  ))

(defn getSettings[]
  @service-settings)

(defn resetSettings[]
  (setSettings 0 100 500))