(ns inventoryService.routes.home
  (:require [inventoryService.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [clojure.java.io :as io]
            [inventoryService.logic.inventory :refer [getSettings]]))

(defn home-page []
  (layout/render
    "home.html" {:settings (assoc (getSettings) :errorRatePct (* 100 (:errorRate (getSettings))))}))


(defroutes home-routes
  (GET "/" [] (home-page))
  )
