(include-book "list-utilities" :dir :teachpacks)
(include-book "io-utilities-ex" :dir :teachpacks)
(include-book "io-file-input-output")
(set-state-ok t)

(defconst *d*  "display.htm") ; display file
(defconst *w*  "world.txt") ; input file (specifies current world)
(defconst *br* (str->chrs "<br>")) ; HTML line separator
(defconst *x* (replicate 2 (chrs->str(append (replicate 12  #\x) *br*))))
(defconst *o* (replicate 8 (chrs->str(append (replicate  3  #\o) *br*))))

(defun xo (n)                                   ; n even -> o column
   (if (= 0 (mod n 2))                          ; n odd -> x row
       *o*
       *x*))
(defun build-display (n)                              ; render world
   (let* ((n-str  (rat->str n 0))
          (n-line (chrs->str(append (str->chrs n-str) *br*))))
         (cons n-line (xo n))))
(defun update-display (n state)
   (mv-let (error-wr-display state)
           (string-list->file *d* (list n) state)
      (if error-wr-display
          (mv error-wr-display state)
          (mv "updated" state))))
(defun ipo1 (state)
  (mv-let (n-str error-open state)                     ; retrieve state of world
	     (file->string *w* state)
     (if error-open
         (mv error-open state)
         (let* ((n (my-transform (str->chrs n-str))) ; update world
                (n+1-str (output-format (car n))))
               (mv-let (error-wr-nw state)
                       (string-list->file *w* (list n+1-str) state)
                  (if error-wr-nw
                      (mv error-wr-nw state)        ; update display
                      (update-display (cadr n) state)))))))

(defun main (state)
   (ipo1 state))
