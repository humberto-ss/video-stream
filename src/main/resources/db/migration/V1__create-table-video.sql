CREATE TABLE VIDEO (

                        ID SERIAL,
                        TITLE VARCHAR(100) NOT NULL,
                        SYNOPSIS VARCHAR(200) NOT NULL,
                        DIRECTOR VARCHAR(100) NOT NULL,
                        ACTORS VARCHAR(200)  NOT NULL,
                        YEAR_RELEASE INTEGER NOT NULL,
                        GENRE VARCHAR(100) NOT NULL,
                        RUNNING_TIME INTEGER NOT NULL,
                        VIEWS INTEGER DEFAULT 0,
                        CONTENT BYTEA,
                        VERSION INTEGER NOT NULL,
                        DELETED BOOLEAN,
                        PRIMARY KEY(ID)
);