(ns to-do-list-api.core
  (:require
   [org.httpkit.server :as http]
   [to-do-list-api.adapters.http.routes :as routes]
   [to-do-list-api.adapters.db.core :as db])
  (:gen-class))

(def PORT 3000)

(defn -main
  [& _]
  (let [{:keys [datasource]} (db/start)
        stop-server (http/run-server
                     routes/app-routes
                     {:port PORT})]

    (println "Server running on port" PORT)

    (.addShutdownHook
     (Runtime/getRuntime)
     (Thread.
      (fn []
        (println "\nShutting down...")
        (stop-server)
        (db/stop datasource))))))
