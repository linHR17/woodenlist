
(ns woodenlist.comp.task-editor
  (:require [hsl.core :refer [hsl]]
            [clojure.string :as string]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span a input button]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [respo.comp.space :refer [comp-space]]))

(defn on-input [mutate!] (fn [e dispatch!] (mutate! (:value e))))

(defn update-state [state text] text)

(defn init-state [& args] "")

(defn on-submit [group-id task-id state mutate!]
  (fn [e dispatch!]
    (if (not (string/blank? state))
      (do
       (mutate! "")
       (dispatch! :task/edit {:task-id task-id, :group-id group-id, :text state})))))

(defn render [task]
  (fn [state mutate!]
    (div
     {}
     (div {} (comp-text (:text task) nil))
     (div
      {}
      (input
       {:style ui/input,
        :event {:input (on-input mutate!)},
        :attrs {:placeholder "Task content", :value state}})
      (comp-space 8 nil)
      (button
       {:style ui/button,
        :event {:click (on-submit (:group-id task) (:id task) state mutate!)}}
       (comp-text "Submit" nil))))))

(def comp-task-editor (create-comp :task-editor init-state update-state render))
