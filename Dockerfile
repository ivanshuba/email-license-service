FROM ubuntu:latest
LABEL authors="freel"

ENTRYPOINT ["top", "-b"]