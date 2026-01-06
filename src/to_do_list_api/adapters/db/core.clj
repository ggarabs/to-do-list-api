(ns to-do-list-api.adapters.db.core
  (:require
   [hikari-cp.core :as hikari]
   [aero.core :as aero]
   [clojure.java.io :as io]))

(defn read-config [file]
  (aero/read-config (io/resource file)))

(defn make-datasource [{:keys [db]}] 
   (hikari/make-datasource 
    {:jdbc-url (:jdbc-url db)
    :username (:username db)
    :password (:password db)
    :maximum-pool-size (:maximum-pool-size db)}))

(defn start []
  (let [config (read-config "db.edn")
        ds (make-datasource config)]
    {:datasource ds}))

(defn stop [datasource]
  (hikari/close-datasource datasource))