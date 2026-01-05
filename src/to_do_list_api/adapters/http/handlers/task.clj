(ns to-do-list-api.adapters.http.handlers.task)

(defn get-all-tasks
  []
  (try 
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body "oi"}
  (catch clojure.lang.ExceptionInfo err
   {:status 500
    :body {:error err}})))