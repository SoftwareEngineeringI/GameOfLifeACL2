(in-package "ACL2")
(include-book "build-avl-tree")
(include-book "io-utilities" :dir :teachpacks)
(include-book "list-utilities" :dir :teachpacks)
(include-book "binary-io-utilities" :dir :teachpacks)
(include-book "char-to-int")
(include-book "int-to-char")
(include-book "iteration")
(set-state-ok t)



#|======================================================================
BUILDING AND SERACHING AVL TREES :-
build-avl-tree
search-avl-tree
compare-avl

(build-avl-tree (list 1 2 34 4 5 6)) =
(3 5 NIL
   (2 2 NIL (1 1 NIL NIL NIL)
      (1 4 NIL NIL NIL))
   (2 6 NIL NIL (1 34 NIL NIL NIL)))

(build-and-search-avl-tree (list 1 2 34 4 5 6) (list 4 5)) = (list 4 5)

build-and-search-avl-tree constructs an avl-tree with the first argument
and searches for a match of every integer in the second argument
and then returns the list of the matches


(serach-avl-tree avl-tree keys) accepts an avl-tree and
a list of integers (keys), searches the avl-tree for a matching 
keys in the list, and then returns the list of 
matching keys


READING STRING FROM INPUT FILE AND CONVERTING INTO LIST OF INTEGERS:-
input-format

(input-format (str->chrs "12 13 0 145 234")) = '(12 13 0 145 234)


WRITING A LIST OF INTEGERS TO FILE OUTPUT:-
output-format

(output-format '(20 10 3 4 5)) = "20 10 3 4 5"



======================================================================|#

;If you wish to perform any arithmetic operation on the list of integers
;return by input-format before passing it to the output-format
;modify add-one function appropiately       
(defun add-zero (xs)
   (if (endp xs)
       xs
       (cons (+ (car xs) 0) (add-zero (cdr xs)))))
     
              
;************************************************************************
;my-tranform takes argument (input-string) from io-format function, calls
;input-format to convert input-string to list of integers
;calls output-format to convert list of integers back to string
;gives the resulting string back to io-format to write to file

(defun my-transform (str)
   (iterator 10 10 (sampletree (input-format str)) 1)) ;list of integers

;************************************************************************
;(io-format "state-of-the-world.txt" "state-of-the-world.txt" STATE)

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
