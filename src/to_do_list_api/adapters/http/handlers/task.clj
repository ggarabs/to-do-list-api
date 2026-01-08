(ns to-do-list-api.adapters.http.handlers.task
  (:require [to-do-list-api.adapters.db.task :as task]
            [malli.core :as m]
            [to-do-list-api.schemas.task :refer [CreateTaskInput]]
            [to-do-list-api.usecases.task :as usecases]))

(defn get-all-tasks [{:keys [db]}]
  (try 
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (task/find-all-tasks db)}
  (catch clojure.lang.ExceptionInfo err
    {:status 500
     :body {:error err}})))

(defn post-task [{:keys [db body]}]
  (let [ds-config db
        task body]
  (if-not (m/validate CreateTaskInput task)
    {:status 400
     :headers {"Content-Type" "application/json"}
     :body {:error "invalid schema"}} 
    (do
      (usecases/create-task! ds-config task)
      {:status 201 
       :headers {"Content-Type" "application/json"} 
       :body {:message "deu bom"}}))
))