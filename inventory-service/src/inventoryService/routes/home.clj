(ns inventoryService.routes.home
  (:require [inventoryService.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [clojure.java.io :as io]
            [inventoryService.logic.inventory :refer [getSettings]]))

(defn home-page []
  (let [settings (getSettings)]
    (layout/render
      "home.html" {:settings (assoc settings :errorRatePct (* 100 (:errorRate settings)))}))
  )


(defroutes home-routes
  (GET "/" [] (home-page))
  )
