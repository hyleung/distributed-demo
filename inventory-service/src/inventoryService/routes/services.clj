(ns inventoryService.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [inventoryService.logic.inventory :refer :all]))

(s/defschema AvailabilityInfo {:id s/Str
                               :inStock s/Bool
                               :count s/Int})

(s/defschema SettingsInfo {:errorRate Double
                           :minLatency s/Int
                           :maxLatency s/Int})

(defapi service-routes
  (ring.swagger.ui/swagger-ui
    "/swagger")
  (swagger-docs
    :title "ABC Company eCommerce")
  (swaggered "Inventory"
    :description "Warehouse Inventory Service"
    (context "/api" []

      (GET* "/availability/:productId" [productId]
        :return AvailabilityInfo
        :query-params []
        :summary "Retrieve availability information for a given product"
        (ok (fetchInventory productId)))
      (GET* "/settings" []
        :return SettingsInfo
        :query-params []
        :summary "Retrieve the current service settings"
        (ok (getSettings)))
      (PUT* "/settings" []
        :body [settings SettingsInfo]
        :summary "Update the service settings"
        (ok (setSettings (:errorRate settings) (:minLatency settings) (:maxLatency settings)))
        )
      )
    )
  )
