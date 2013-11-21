
;New char-to-int
(in-package "ACL2")
(include-book "io-utilities" :dir :teachpacks)
(include-book "binary-io-utilities" :dir :teachpacks)



(defun is-a-digit (x)
   (if (or (equal x #\1)
           (equal x #\2)
           (equal x #\3)
           (equal x #\4)
           (equal x #\5)
           (equal x #\6)
           (equal x #\7)
           (equal x #\8)
           (equal x #\9)
           (equal x #\0))
       t
       nil))

#|=====================================================================
remove-non-digit removes any non-digit char from a list of characters
(check-if-is-a-digit '(#\Space #\- #\- #\3 #\. #\2 #\+ #\4)) 
                                                    = (#\3 #\2 #\4)
=====================================================================|#
(defun remove-non-digit(xs)
   (cond ((endp xs) nil)
         ((is-a-digit (car xs))
          (cons (car xs) (remove-non-digit (cdr xs))))
         (t (remove-non-digit (cdr xs)))))
       



(defun split-at-delimiter (ds xs)
  (if (or (endp xs) (member-equal (car xs) ds))
      (list nil xs)
      (let* ((cdr-split (split-at-delimiter ds (cdr xs))))
        (list (cons (car xs) (car cdr-split))
              (cadr cdr-split)))))



; (transform (str->chrs "12 13 0 145 234")) = ((#\1 #\2) (#\1 #\3) (#\0) (#\1 #\4 #\5) (#\2 #\3 #\4))
(defun transform (xs)
   (cond ((endp xs) nil)
         ((not (is-a-digit (car xs)))
          (transform (cdr xs)))
         (t (let* ((split (split-at-delimiter '(#\Space) xs))
              (token (remove-non-digit (car split)))
              (rest-xs (cdr (cadr split))))
             (cons token (transform rest-xs)))))) ;list of lists with #\Space del


;(format-transform '((#\3) (#\4 #\6 #\6) (#\5) (#\2 #\3) (#\0) (#\0)))
; = (3 466 5 23 0 0)
(defun format-transform (xs)
   (if (endp xs)
       xs
       (let* ((first-val (car xs))
              (int-val (str->int (chrs->str first-val)))) 
             (cons int-val (format-transform (cdr xs))))))

;(input-format (str->chrs "12 13 0 145 234")) = '(12 13 0 145 234)
;**********************************************************************************************************************
(defun input-format (xs)
   (let* ((split (transform xs))
          (val (format-transform split)))
         val))


