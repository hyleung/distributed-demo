(defproject availabilityService "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring-server "0.4.0"]
                 [selmer "0.8.2"]
                 [com.taoensso/timbre "3.4.0"]
                 [com.taoensso/tower "3.0.2"]
                 [markdown-clj "0.9.65"]
                 [environ "1.0.0"]
                 [im.chit/cronj "1.4.3"]
                 [compojure "1.3.3"]
                 [ring/ring-defaults "0.1.4"]
                 [ring/ring-session-timeout "0.1.0"]
                 [ring-middleware-format "0.5.0"]
                 [noir-exception "0.2.3"]
                 [bouncer "0.3.2"]
                 [prone "0.8.1"]
                 [metosin/compojure-api "0.19.3"]
                 [metosin/ring-swagger-ui "2.1.0-M2-2"]
                 [clj-zipkin "0.1.3"]
                 [clj-tuple "0.2.2"]]
            
  :min-lein-version "2.0.0"
  :uberjar-name "inventoryService.jar"
  :repl-options {:init-ns inventoryService.handler}
  :jvm-opts ["-server"]

  :main inventoryService.core

  :plugins [[lein-ring "0.9.1"]
            [lein-environ "1.0.0"]
            [lein-ancient "0.6.5"]
            [com.palletops/uberimage "0.4.1"]
            ]
  

  

  :ring {:handler inventoryService.handler/app
         :init    inventoryService.handler/init
         :destroy inventoryService.handler/destroy
         :uberwar-name "availabilityService.war"}
  
  
  
  :profiles
  {:uberjar {:omit-source true
             :env {:production true}
             
             :aot :all}
   :dev {:dependencies [[ring-mock "0.1.5"]
                        [ring/ring-devel "1.3.2"]
                        [pjstadig/humane-test-output "0.7.0"]
                        [midje "1.6.3"]
                        ]
         :source-paths ["env/dev/clj"]
         
         
         
         :repl-options {:init-ns inventoryService.repl}
         :injections [(require 'pjstadig.humane-test-output)
                      (pjstadig.humane-test-output/activate!)]
         :env {:dev true}}})
