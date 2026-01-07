(ns to-do-list-api.infra.middleware
  (:require [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(defn wrap-db [handler db]
  (fn [request]
    (handler (assoc request :db db))))

(defn wrap-http [handler]
  (-> handler 
      (wrap-json-body {:keywords? true :malformed-response? true}) 
      (wrap-json-response {:charset "utf-8"})))

(defn wrap-app [handler datasource]
  (-> handler
      (wrap-db datasource)
      wrap-http))