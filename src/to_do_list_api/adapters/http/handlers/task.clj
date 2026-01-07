(ns to-do-list-api.adapters.http.handlers.task
  (:require [to-do-list-api.adapters.db.task :as task]))

(defn get-all-tasks [{:keys [db]}]
  (try 
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (task/find-all-tasks db)}
  (catch clojure.lang.ExceptionInfo err
    {:status 500
     :body {:error err}})))