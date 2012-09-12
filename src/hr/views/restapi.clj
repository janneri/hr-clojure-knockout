(ns hr.views.restapi
  (:use [cheshire.core :only [generate-string]])
  (:use [noir.core :only [defpage]])
  (:use [hr.dao :as dao]))


(defpage "/api/departments" []
         (generate-string (dao/list-departments-with-empcount)))

(defpage [:post "/api/add-department"] {department :json}
         (str (dao/add-department department)))

(defpage [:post "/api/delete-department"] {department :json}
         (str (dao/delete-department department)))
