(ns inventoryService.logic.inventory)

(defrecord ServiceSettings [errorRate minLatency maxLatency])

(def service-settings (atom (ServiceSettings. 0.6 100 1000)))

;;roll the dice for an error
(defn roll-dice[errorRate]
  (< rand errorRate))

(defn fetchCount[]
  100)

(defn fetchInventory[productId]
  {:id productId :inStock true :count (fetchCount )})

(defn setSettings[errorRate minLatency maxLatency]
  (do
    (swap! service-settings (fn[x] (ServiceSettings. errorRate minLatency maxLatency))) @service-settings
  ))

(defn getSettings[]
  @service-settings)