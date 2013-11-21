(in-package "ACL2")
(include-book "io-utilities" :dir :teachpacks)
(include-book "binary-io-utilities" :dir :teachpacks)



;Given a list of strings of integers, it converts list to a single
;string of integers
;E.g (combine-list-of-string '("4" "5" "7")) = "457"
;(combine-list-of-string '("4" " " "5" "7")) = "4 57"
(defun combine-list-of-string (xs)
   (if (not (consp (cdr xs)))
       (car xs)
       (String-append (car xs) (combine-list-of-string (cdr xs)))))

;helper
(defun convert-int-list-of-string (xs)
   (if (not (consp (cdr xs)))
       (list (rat->str (car xs) 0))
       (let* ((a (list (rat->str (car xs) 0))))
             (append a (list " ") (convert-int-list-of-string (cdr xs))))))


;(output-format '(20 10 3 4 5)) = "20 10 3 4 5"
;****************************************************************************************
(defun output-format (xs)
   (if (not (consp xs))
       xs
       (let* ((a (convert-int-list-of-string xs))
              (b (combine-list-of-string a)))
             b)))

;**********************************************************************