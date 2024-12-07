CREATE TABLE UserInformation (
    empID varchar(13) PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    roleID VARCHAR(10),
    FOREIGN KEY (roleID) REFERENCES Role(roleID)
);

CREATE TABLE Role (
    roleID VARCHAR(10) PRIMARY KEY,
    roleName VARCHAR(20) NOT NULL
);

CREATE TABLE MealType (
    mealTypeID VARCHAR(3) PRIMARY KEY,
    mealTypeName VARCHAR(20) NOT NULL
);

CREATE TABLE FoodMenuItem (
    itemID INT PRIMARY KEY,
    nameOfFood VARCHAR(30) NOT NULL,
    foodPrice DECIMAL(5, 2) NOT NULL,
    foodAvailable BOOLEAN NOT NULL
);

CREATE TABLE FoodMenuItemMealType (
    itemID INT,
    mealTypeID VARCHAR(3),
    PRIMARY KEY (itemID, mealTypeID),  
    FOREIGN KEY (itemID) REFERENCES FoodMenuItem(itemID),
    FOREIGN KEY (mealTypeID) REFERENCES MealType(mealTypeID)
);


CREATE TABLE UserFeedbackOnFoodItem (
    feedbackID INT PRIMARY KEY ,
    empID varchar(13) NOT NULL,
    itemID INT NOT NULL,
    feedbackGivenDate DATE NOT NULL,
    hygieneRating INT CHECK(hygieneRating BETWEEN 1 AND 5),
    qualityRating INT CHECK(qualityRating BETWEEN 1 AND 5),
    tasteRating INT CHECK(tasteRating BETWEEN 1 AND 5),
    feedbackMessage TINYTEXT,
    UNIQUE (empID, itemID, feedbackGivenDate),
    FOREIGN KEY (empID) REFERENCES UserInformation(empID),
    FOREIGN KEY (itemID) REFERENCES FoodMenuItem(itemID)
);

CREATE TABLE FeedbackSummary (
    summeryID INT PRIMARY KEY,
    itemID INT,
    date DATE NOT NULL,
    averageHygieneRating DECIMAL(2, 1),
    averageQualityRating DECIMAL(2, 1),
    averageTasteRating DECIMAL(2, 1),
    overallSentiment TINYTEXT,
    FOREIGN KEY (itemID) REFERENCES FoodMenuItem(itemID)
);

CREATE TABLE ChefRecommendation (
    recommendationID INT PRIMARY KEY,
    recommendationDate DATE NOT NULL,
    itemID VARCHAR(10) NOT NULL,
    mealTypeID VARCHAR(3) NOT NULL,
    votingEndTime DATETIME NOT NULL,
    FOREIGN KEY (itemID) REFERENCES FoodMenuItem(itemID),
    FOREIGN KEY (mealTypeID) REFERENCES MealType(mealTypeID)
);

CREATE TABLE Vote (
    empID varchar(13),
    votegivenDate DATE,
    mealTypeID VARCHAR(3),
    itemID INT,
    FOREIGN KEY (empID) REFERENCES UserInformation(empID),
    FOREIGN KEY (mealTypeID) REFERENCES MealType(mealTypeID),
    FOREIGN KEY (itemID) REFERENCES FoodMenuItem(itemID),
    UNIQUE(empID, votegivenDate, mealTypeID)
);
