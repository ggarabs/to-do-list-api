(ns to-do-list-api.adapters.http.handlers.task
  (:require [to-do-list-api.adapters.db.task :as task]
            [malli.core :as m]
            [to-do-list-api.schemas.task :refer :all]
            [to-do-list-api.usecases.task :as usecases]
            [malli.transform :as mt]))

(defn get-all-tasks [{:keys [db]}]
  (try
    {:status 200
     :body (task/find-all-tasks db)}
    (catch clojure.lang.ExceptionInfo err
      {:status 500
       :body {:error err}})))

(defn get-task-by-id [{:keys [db route-params]}]
  (try
    (let [id (m/coerce [:uuid] (:id route-params) mt/string-transformer)
          ds-config db
          response (usecases/find-task-by-id! ds-config id)]
      {:status 200 :body response})
    (catch clojure.lang.ExceptionInfo err
      (case (:type (ex-data err))
        :not-found {:status 404 :body {:error "task not found"}}
        {:status 500 :body {:error "internal server error"}}))))

(defn post-task [{:keys [db body]}]
  (let [ds-config db
        task body]
    (if-not (m/validate CreateTaskInput task)
      {:status 422
       :body {:error "invalid schema"}}
      (do
        (usecases/create-task! ds-config task)
        {:status 201
         :body {:message "task created successfully"}}))))

(defn modify-task [{:keys [db route-params body]}]
  (try
    (let [id (m/coerce [:uuid] (:id route-params) mt/string-transformer)
          ds-config db
          changes body]
      (when-not (m/validate ModifyTaskInput changes)
        (throw (ex-info "invalid body" {:type :invalid-body})))
      (usecases/modify-task! ds-config id changes) 
      {:status 204})
    (catch clojure.lang.ExceptionInfo err
      (case (:type (ex-data err))
        :invalid-body {:status 422 :body {:error "invalid body"}}
        :task/not-found {:status 404 :body {:error "task not found"}}
        {:status 400 :body {:error "invalid id"}}))))

(defn remove-task [{:keys [db route-params body]}]
  (try
    (let [id (m/coerce [:uuid] (:id route-params) mt/string-transformer)
          ds-config db]
      (when-not (nil? body)
        (throw (ex-info "body not recognized" {:type :invalid-body})))
      (usecases/remove-task! ds-config id)
      {:status 200 :body {:message "task removed successfully"}})
    (catch clojure.lang.ExceptionInfo err
      (case (:type (ex-data err))
        :invalid-body {:status 422 :body {:error "body not recognized"}}
        :task/not-found {:status 404 :body {:error "task not found"}}
        {:status 400 :body {:error "invalid id"}}))))