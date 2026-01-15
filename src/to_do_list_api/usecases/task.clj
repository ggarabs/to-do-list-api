(ns to-do-list-api.usecases.task
  (:require [to-do-list-api.adapters.db.task :as db]))

(defn create-task! [db-source task]
  (db/insert-task db-source task))

(defn modify-task! [db-source id changes]
  (let [response-body (db/alter-task db-source id changes)]
    (when (zero? (:next.jdbc/update-count response-body))
      (throw (ex-info "task not found" {:type :not-found})))))