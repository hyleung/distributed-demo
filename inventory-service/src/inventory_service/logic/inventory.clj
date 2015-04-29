(ns inventory-service.logic.inventory)

;;roll the dice for an error
(defn roll-dice[errorRate]
  (< rand errorRate))

(defn fetchCount[]
  100)

(defn fetchInventory[productId]
  {:id productId :inStock true :count (fetchCount )})

