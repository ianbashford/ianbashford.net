+++
date = "2017-10-10T21:24:33Z"
title = "Vim and Python in Conda"
draft = "false"

+++
## vim and conda
### conda
```sh
wget -S https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh 
```
and run but DON'T allow it to add miniconda to the PATH (yet)
note you'll replace /dev/shm with your choice location
```sh
alias sa='source /dev/shm/miniconda3/bin/activate'
alias sd='source /dev/shm/miniconda3/bin/deactivate'
alias conda='/dev/shm/miniconda3/bin/conda'
```
### compile vim
note you'll replace /dev/shm with your choice location
```sh
git clone https://github.com/vim/vim.git
make uninstall && make clean && make distclean && \
./configure --with-features=huge --enable-pythoninterp --enable-python3interp --prefix=/dev/shm/vim_install/ && \
make VIMRUNTIMEDIR=/dev/shm/vim_install/share/vim/vim80 && make install
```
verify python is OK (this will use your system python for now
```vim
:python3 import sys; print(sys.version)
```
### setup vim
#### add Vundle
```sh
git clone https://github.com/gmarik/Vundle.vim.git ~/.vim/bundle/Vundle.vim
```
#### .vimrc
note the cjrh vim-conda - this is niiice
```sh
set nocompatible              " required
filetype off                  " required

" set the runtime path to include Vundle and initialize
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

" alternatively, pass a path where Vundle should install plugins
"call vundle#begin('~/some/path/here')

" let Vundle manage Vundle, required
Plugin 'gmarik/Vundle.vim'
Plugin 'cjrh/vim-conda' 
Plugin 'davidhalter/jedi-vim'
Plugin 'nvie/vim-flake8'
" Add all your plugins here (note older versions of Vundle used Bundle instead of Plugin)


" All of your Plugins must be added before the following line
call vundle#end()            " required
filetype plugin indent on    " required
```
Vundle will install after you start vim and type
```sh
:PluginInstall
```
and install flake8 with 
```sh
pip install flake8
```
### test your conda 
```sh
conda create -n a python=3.6
source activate a
```
now rerun the python test and you'll see you're running conda python
same thing if you run 
```sh
:!python3 %
```
### finish off vim setup
here's a couple of guides [heaven](https://realpython.com/blog/python/vim-and-python-a-match-made-in-heaven/) or [vim and python](https://www.fullstackpython.com/vim.html)


### final .vimrc
```sh
set nocompatible              " required
filetype off                  " required

" set the runtime path to include Vundle and initialize
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

" alternatively, pass a path where Vundle should install plugins
"call vundle#begin('~/some/path/here')

" let Vundle manage Vundle, required
Plugin 'gmarik/Vundle.vim'

" Add all your plugins here (note older versions of Vundle used Bundle instead of Plugin)
Plugin 'cjrh/vim-conda'
Plugin 'davidhalter/jedi-vim'
Plugin 'nvie/vim-flake8'

" code folding, defined down below
Plugin 'tmhedberg/SimpylFold'
 " auto indentation
Plugin 'vim-scripts/indentpython.vim'
" syntax check on save
Plugin 'scrooloose/syntastic'
" powerline
Plugin 'Lokaltog/powerline', {'rtp': 'powerline/bindings/vim/'}


" All of your Plugins must be added before the following line
call vundle#end()            " required
filetype plugin indent on    " required

set splitbelow
set splitright

highlight BadWhitespace ctermbg=red guibg=red

"split navigations
nnoremap <C-J> <C-W><C-J>
nnoremap <C-K> <C-W><C-K>
nnoremap <C-L> <C-W><C-L>
nnoremap <C-H> <C-W><C-H>

" Enable folding
set foldmethod=indent
set foldlevel=99
" Enable folding with the spacebar
nnoremap <space> za

" PEP8 indentations
" au BufNewFile,BufRead *.py
    " \ set tabstop=4
    " \ set softtabstop=4
    " \ set shiftwidth=4
    " \ set textwidth=79
    " \ set expandtab
    " \ set autoindent
    " \ set fileformat=unix
" Python, PEP-008
au BufRead,BufNewFile *.py,*.pyw set expandtab
au BufRead,BufNewFile *.py,*.pyw set textwidth=139
au BufRead,BufNewFile *.py,*.pyw set tabstop=4
au BufRead,BufNewFile *.py,*.pyw set softtabstop=4
au BufRead,BufNewFile *.py,*.pyw set shiftwidth=4
au BufRead,BufNewFile *.py,*.pyw set autoindent
au BufRead,BufNewFile *.py,*.pyw match BadWhitespace /^\t\+/
au BufRead,BufNewFile *.py,*.pyw match BadWhitespace /\s\+$/
au         BufNewFile *.py,*.pyw set fileformat=unix
au BufRead,BufNewFile *.py,*.pyw let b:comment_leader = '#'

set encoding=utf-8
let python_highlight_all=1
syntax on
set nu
```
