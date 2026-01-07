(ns to-do-list-api.core
  (:require
   [org.httpkit.server :as http]
   [to-do-list-api.adapters.http.routes :refer [app-routes]]
   [to-do-list-api.adapters.db.core :as db]
   [to-do-list-api.infra.middleware :refer [wrap-app]])
  (:gen-class))

(def PORT 3000)

(defn -main
  [& _]
  (let [{:keys [datasource]} (db/start)
        app (wrap-app app-routes datasource)
        stop-server (http/run-server app {:port PORT})]

    (println "Server running on port" PORT)

    (.addShutdownHook
     (Runtime/getRuntime)
     (Thread.
      (fn []
        (println "\nShutting down...")
        (stop-server)
        (db/stop datasource))))))
