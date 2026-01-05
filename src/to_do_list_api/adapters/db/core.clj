(ns to-do-list-api.adapters.db.core
  (:require
   [hiraki-cp.core :as hiraki]
   [aero.core :refer [read-config]]))

(defonce datasource
  (delay 
    (let [config (read-config "config.edn")
          db (:db config)]
     (hiraki/make-datasource
     {:jdbc-url (:jdbc-url db)
      :username (:username db)
      :password (:password db)
      :maximum-pool-size (:maximum-pool-size db)}))))

(defn get-datasource
  []
  @datasource)

(defn get-db
  []
  {:datasource (get-datasource)})