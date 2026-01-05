(ns to-do-list-api.adapters.db.core
  (:require
   [hikari-cp.core :as hikari]
   [aero.core :as aero]
   [clojure.java.io :as io]))

(defn read-config []
  (aero/read-config (io/resource "config.edn")))

(defn make-datasource [{:keys [db]}] 
   (hikari/make-datasource 
    {:jdbc-url (:jdbc-url db)
    :username (:username db)
    :password (:password db)
    :maximum-pool-size (:maximum-pool-size db)}))

(defn start []
  (let [config (read-config)
        ds (make-datasource config)]
    {:datasource ds}))

(defn stop [datasource]
  (hikari/close-datasource datasource))