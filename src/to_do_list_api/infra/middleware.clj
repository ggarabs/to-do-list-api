(ns to-do-list-api.infra.middleware
  (:require 
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [clojure.walk :as walk]))

(defn remove-keywords [data]
  (walk/postwalk
   (fn [entry]
     (if (keyword? entry)
       (keyword (name entry))
       entry))
   data))

(defn instant->string [data]
  (walk/postwalk
   (fn [entry]
     (if (instance? java.time.Instant entry)
       (.toString entry)
       entry))
   data))

(defn wrap-instant->string [handler]
  (fn [request]
    (let [response (handler request)]
      (if (coll? (:body response))
        (update response :body instant->string)
        response))))

(defn wrap-db [handler db]
  (fn [request]
    (handler (assoc request :db db))))

(defn wrap-remove-keywords [handler]
  (fn [request]
    (let [response (handler request)] 
      (if (coll? (:body response)) 
        (update response :body remove-keywords) 
        response))
    ))

(defn wrap-http [handler]
  (-> handler 
      (wrap-json-body {:keywords? true 
                       :malformed-response? true})
      wrap-remove-keywords
      wrap-instant->string
      (wrap-json-response {:charset "utf-8"})))

(defn wrap-app [handler datasource]
  (-> handler
      (wrap-db datasource)
      wrap-http))