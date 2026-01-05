(ns to-do-list-api.core
  (:require
   [org.httpkit.server :as http]
   [to-do-list-api.adapters.http.routes :as routes])
  (:gen-class))

(def PORT 3000)

(defn -main
  [& _]
  (http/run-server routes/app-routes {:port PORT})
  (println (str "Server running on port " PORT)))
