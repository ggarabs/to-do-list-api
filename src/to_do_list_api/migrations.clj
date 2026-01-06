(ns to-do-list-api.migrations 
  (:require
    [migratus.core :as migratus]
    [to-do-list-api.adapters.db.core :as db])
  (:gen-class))

(defn -main [& _]
  (println "Running DB migrations...")
  (migratus/migrate (db/read-config "migratus.edn"))
  (println "Migrations finished"))