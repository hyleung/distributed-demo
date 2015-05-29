(ns inventoryService.logic.inventory)

;;(defrecord ServiceSettings [errorRate minLatency maxLatency errorLatency])

;;(def service-settings (atom (ServiceSettings. 0 100 500 2000)))
(def service-settings (atom {:errorRate 0  :minLatency 100 :maxLatency 500  :errorLatency 2000}))

(defn exec-after-delay[timeout f]
  (Thread/sleep timeout)
  (f))

;;roll the dice for an error
(defn roll-dice[errorRate]
  (< (rand) errorRate))

(defn random-delay[]
  (if (< (- (:maxLatency @service-settings) (:minLatency @service-settings)) 10)
       10
       (first
         (shuffle (range (:minLatency @service-settings) (:maxLatency @service-settings) 10)))))

(defn fetchCount[]
  (first (shuffle (range 0 500 10))))

(defn fetchInventory[productId]
  (if (roll-dice (:errorRate @service-settings))
    (exec-after-delay (:errorLatency @service-settings) #(throw (Exception. "error")))
    (exec-after-delay (random-delay) #(eval {:id productId :inStock true :count (fetchCount )}))
    )
  )

(defn setSettings[errorRate minLatency maxLatency errorLatency]
  (do
    (swap! service-settings (fn[x] {:errorRate errorRate  :minLatency minLatency :maxLatency maxLatency  :errorLatency errorLatency})) @service-settings
  ))

(defn getSettings[]
  @service-settings)

(defn resetSettings[]
  (setSettings 0 100 500 2000))
