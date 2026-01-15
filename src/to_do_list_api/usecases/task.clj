(ns to-do-list-api.usecases.task
  (:require [to-do-list-api.adapters.db.task :as db]))

(defn create-task! [db-source task]
  (db/insert-task db-source task))

(defn modify-task! [db-source id changes]
  (let [uuid (try
               (parse-uuid id)
               (catch Exception _ nil))
        response-body (db/alter-task db-source uuid changes)] 
    (when (zero? (:next.jdbc/update-count response-body))
      (throw (ex-info "task not found" {:type :not-found})))))

(defn remove-task! [db-source id] 
  (println id)
  (let [response-body (db/delete-task db-source id)]
    (when (zero? (:next.jdbc/update-count response-body))
      (throw (ex-info "task not found" {:type :not-found})))))