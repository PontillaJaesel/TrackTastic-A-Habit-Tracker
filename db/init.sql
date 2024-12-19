USE final_project;

CREATE TABLE users (
    user_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) PRIMARY KEY,       
    password VARCHAR(255) NOT NULL,        
    bio TEXT                               
);

CREATE TABLE habits (
    habit_name VARCHAR(100) PRIMARY KEY,   
    description TEXT,                      
    status VARCHAR(50),                    
    goal TEXT,                             
    tag VARCHAR(50)                        
);
