(ns to-do-list-api.adapters.http.routes
    (:require 
     [compojure.core :refer [defroutes GET POST]] 
     [compojure.route :as route] 
     [to-do-list-api.adapters.http.handlers.task :as task]))

(defroutes app-routes
  (GET "/" [] {:body {:message "API ok"}})
  (GET "/task" req (task/get-all-tasks req))
  (POST "/task" req (task/post-task req))
  (route/not-found {:status 404
                    :body {:error "Not found"}}))