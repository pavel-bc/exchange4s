docs:
	sbt doc
	mkdir -p ./docs
	cp -R ./target/scala-2.13/api/* ./docs

.PHONY: docs