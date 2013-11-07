(in-package "ACL2")
(include-book "arithmetic-3/top":dir :system)
(include-book "io-utilities" :dir :teachpacks)
(include-book "list-utilities" :dir :teachpacks)
(include-book "binary-io-utilities" :dir :teachpacks)
;(include-book "avl-rational-keys" :dir :teachpacks)

(set-state-ok t)


(defun split-at-delimiter (ds xs)
  (if (or (endp xs) (member-equal (car xs) ds))
      (list nil xs)
      (let* ((cdr-split (split-at-delimiter ds (cdr xs))))
        (list (cons (car xs) (car cdr-split))
              (cadr cdr-split)))))


(defun mult-integer (n x)
   (* n x))

(defun integer-value (x)
   (chr->dgt x))

(defun incr-unit (x)
   (if (= x 0)
       1
       (* x 10)))
   

(defun convert-to-int (n xs)
   (if (endp xs)
       0
       (let* ((mult (incr-unit n))
              (token (integer-value (car xs)))
              (result (+ (* mult token) (convert-to-int mult (cdr xs)))))
             result)))


(defun remove-space (xs)
   (cond ((endp xs) xs)
         ((equal (car xs) #\Space)
          (remove-space (cdr xs)))
         (t xs)))


; (transform (str->chrs "12 13 0 145 234")) = ((#\1 #\2) (#\1 #\3) (#\0) (#\1 #\4 #\5) (#\2 #\3 #\4))
;alternatives
(defun transform (xs)
   (cond ((endp xs) nil)
         ((equal(car xs) #\Space)
          (transform (cdr xs)))
         (t (let* ((split (split-at-delimiter '(#\Space) xs))
              (token (car split))
              (rest-xs (cdr (cadr split))))
             (cons token (transform rest-xs)))))) ;list of lists with #\Space del




(defun format-transform (xs)
   (if (endp xs)
       xs
       (let* ((first-val (car xs))
              (int-val (convert-to-int 0 (reverse first-val))))
             (cons int-val (format-transform (cdr xs))))))



;Given a list of string of integers, it converts to a single
;string of integers
(defun combine-list-of-string (xs)
   (if (not (consp (cdr xs)))
       (car xs)
       (String-append (car xs) (combine-list-of-string (cdr xs)))))

'helper
(defun convert-int-list-of-string (xs)
   (if (not (consp (cdr xs)))
       (list (rat->str (car xs) 0))
       (let* ((a (list (rat->str (car xs) 0))))
             (append a (list " ") (convert-int-list-of-string (cdr xs))))))


;(input-format (str->chrs "12 13 0 145 234"))
;**********************************************************************************************************************
(defun input-format (xs)
   (let* ((split (transform xs))
          (val (format-transform split)))
         val))

;*********************************************************************************************************************


;(output-format '(20 10 3 4 5)) = "20 10 3 4 5"
;****************************************************************************************
(defun output-format (xs)
   (if (not (consp xs))
       xs
       (let* ((a (convert-int-list-of-string xs))
              (b (combine-list-of-string a)))
             b)))

;**********************************************************************************
   
              
;If you wish to perform any arithmetic operation on the list of integers
;return by input-format before passing it to the output-format
;modify add-one function appropiately       
(defun add-one (xs)
   (if (endp xs)
       xs
       (cons (+ (car xs) 0) (add-one (cdr xs)))))
     
              
;************************************************************************
;my-tranform takes argument (input-string) from io-format function, calls input-format to
;convert input-string to list of integers
;calls output-format to convert list of integers back to string
;gives the resulting string back to io-format to write to file
(defun my-transform (str)
   (let* ((a (add-one (input-format str))) ;list of integers
          (b (output-format a))) ;string of integers
         b))

;************************************************************************


(defun io-format  (f-in f-out state)
   (mv-let (input-put-as-string error-open state) 
           (file->string f-in state)
     (if error-open
         (mv error-open state)
         (mv-let (error-close state)
                 (string-list->file f-out (list (my-transform (str->chrs input-put-as-string))) state)
            (if error-close
                (mv error-close state)
                (mv (string-append "input file: "
                     (string-append f-in
                      (string-append ", output file: " f-out)))
                    state))))))



   