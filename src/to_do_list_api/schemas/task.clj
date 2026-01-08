(ns to-do-list-api.schemas.task
  (:gen-class))

(def CreateTaskInput
  [:map
   [:title [:string {:min 5, :max 50}]]
   [:description {:optional true} [:string]]
   [:status {:optional true} 
    [:enum "pendente" "em_curso" "concluido"]]])