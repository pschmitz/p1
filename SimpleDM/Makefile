A2PS=a2ps
JAVAC=javac
JAR=jar
JAR_FILE=simpledm
LIB_DIR=lib
DOC_DIR=doc
PACKAGE_PREFIX=edu
SRC_FILES = \
	./src/edu/iit/ir/simpledm/data/*.java	    \
	./src/edu/iit/ir/simpledm/exception/*.java  \
	./src/edu/iit/ir/simpledm/util/*.java	    \
	./src/edu/iit/ir/simpledm/preprocess/*.java \
	./src/edu/iit/ir/simpledm/model/*.java	    \
	./src/edu/iit/ir/simpledm/model/svm/*.java  \
	./src/edu/iit/ir/simpledm/app_mng/*.java	

all:
	$(JAVAC) -d . -classpath . $(SRC_FILES)

jar:
	$(JAR) cf $(JAR_FILE).jar $(PACKAGE_PREFIX)
	mv $(JAR_FILE).jar $(LIB_DIR)

ps:	
	$(A2PS) -A fill -C -4 -o $(DOC_DIR)/src.ps $(SRC_FILES)

clean:
	rm -rf $(PACKAGE_PREFIX)
	rm -f $(LIB_DIR)/$(JAR_FILE).jar
	rm -f $(DOC_DIR)/src.ps

