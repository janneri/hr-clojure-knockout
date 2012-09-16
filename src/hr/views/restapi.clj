(ns hr.views.restapi
  (:use [cheshire.core :only [generate-string]])
  (:use [noir.core :only [defpage]])
  (:use [hr.dao :as dao]))


(defpage "/api/departments" []
         (generate-string (dao/list-departments-with-empcount)))

(defpage "/api/department/:deptid/employees" {:keys [deptid]}
         (generate-string (dao/emps-of-dept (Integer/parseInt deptid))))

(defpage [:post "/api/add-department"] {department :json}
         (str (dao/add-department department)))

(defpage [:post "/api/delete-department"] {department :json}
         (str (dao/delete-department department)))

(defpage [:post "/api/add-employee"] {employee :json}
         (str (dao/add-employee employee)))

(defpage [:post "/api/delete-employee"] {emp :json}
         (str (dao/delete-employee emp)))
