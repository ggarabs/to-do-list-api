(ns to-do-list-api.usecases.task
  (:require [to-do-list-api.adapters.db.task :as db]))

(defn find-task-by-id! [db-source id]
  (if-let [task (db/find-task-by-id db-source id)]
    task
    (throw (ex-info "task not found" {:type :not-found}))))

(defn create-task! [db-source task]
  (db/insert-task db-source task))

(defn modify-task! [db-source id changes]
  (let [response-body (db/alter-task db-source id changes)]
    (when (zero? (:next.jdbc/update-count response-body))
      (throw (ex-info "task not found" {:type :not-found})))))

(defn remove-task! [db-source id] 
  (let [response-body (db/delete-task db-source id)]
    (when (zero? (:next.jdbc/update-count response-body))
      (throw (ex-info "task not found" {:type :not-found})))))