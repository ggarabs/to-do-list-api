(ns to-do-list-api.usecases.task
  (:require [to-do-list-api.adapters.db.task :as db]))

(defn create-task! [db-source task]
  (db/insert-task db-source task))