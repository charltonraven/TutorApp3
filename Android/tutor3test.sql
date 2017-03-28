-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 29, 2017 at 01:27 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tutor3test`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `departID` int(11) NOT NULL,
  `courseNumber` varchar(4) NOT NULL,
  `sectionNumber` int(5) NOT NULL,
  `teacherID` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`departID`, `courseNumber`, `sectionNumber`, `teacherID`) VALUES
(1000, '201', 380, NULL),
(1000, '421', 4088, NULL),
(1001, '101', 380, NULL),
(1001, '207', 380, NULL),
(1002, '104', 380, NULL),
(1002, '305', 4088, NULL),
(1003, '102', 4088, NULL),
(1004, '190', 6574, NULL),
(1004, '401', 380, NULL),
(1004, '480A', 4622, NULL),
(1006, '250', 6574, NULL),
(1008, '132', 4088, NULL),
(1008, '201', 6574, NULL),
(1009, '201', 6574, NULL),
(1010, '314', 4088, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `departID` int(11) NOT NULL,
  `departName` varchar(25) NOT NULL,
  `departAbbr` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`departID`, `departName`, `departAbbr`) VALUES
(1000, 'Accounting', 'ACTG'),
(1001, 'Art', 'ART'),
(1002, 'Biology', 'BIOL'),
(1003, 'Chemistry', 'CHEM'),
(1004, 'Computer Science', 'CS'),
(1005, 'Economics', 'ECON'),
(1006, 'English', 'ENG'),
(1007, 'French', 'FNCH'),
(1008, 'Mathematics', 'MATH'),
(1009, 'Physics', 'PHYS'),
(1010, 'Spanish', 'SPAN');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `studentID` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `firstName` varchar(15) NOT NULL,
  `lastName` varchar(15) NOT NULL,
  `major` varchar(30) DEFAULT NULL,
  `courses` varchar(50) DEFAULT NULL,
  `registered` tinyint(1) NOT NULL,
  `tutor` tinyint(1) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentID`, `email`, `firstName`, `lastName`, `major`, `courses`, `registered`, `tutor`, `password`) VALUES
('cwilliams2638', 'cwilliams2638@g.fmarion.edu', 'Charlton', 'Williams', 'Biology', NULL, 1, 1, 'password'),
('dleon6779', 'dleon6779@g.fmarion.edu', 'Dylan', 'Leon', 'Computer Science', NULL, 1, 0, 'password'),
('mattwhit', 'mattwhit@g.fmarion.edu', 'Matt', 'Whitley', 'English', '', 1, 0, 'password'),
('StudentTest234', 'StudentTest234@g.fmarion.edu', 'Student', 'Test', 'Computer Science', NULL, 1, 0, 'password'),
('TestTutor4525', 'TestTutor4525@g.fmarion.edu', 'Test', 'Tutor', 'Physics', NULL, 1, 1, 'password'),
('TutorTest1235', 'TutorTest1235@g.fmarion.edu', 'Tutor', 'Test', 'Computer Science', NULL, 1, 1, 'password');

-- --------------------------------------------------------

--
-- Table structure for table `student_chathistory`
--

CREATE TABLE `student_chathistory` (
  `studentID` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `classname` varchar(50) NOT NULL,
  `FromID` varchar(50) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_chathistory`
--

INSERT INTO `student_chathistory` (`studentID`, `email`, `name`, `classname`, `FromID`, `date`) VALUES
('cwilliams2638', 'cwilliams2638@g.fmarion.edu', 'Charlton Williams', 'MATH132', 'dleon6779', '2017-03-26');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `teacherID` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `firstName` varchar(15) NOT NULL,
  `lastName` varchar(15) NOT NULL,
  `department` varchar(25) NOT NULL,
  `registered` tinyint(2) DEFAULT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`teacherID`, `email`, `firstName`, `lastName`, `department`, `registered`, `password`) VALUES
('blahblah343', 'blahblah343@g.fmarion.edu', 'Ace', 'IntheHole', 'Mathematics', 1, 'password'),
('BZeech3433', 'BZeech3433@g.fmarion.edu', 'Breech', 'Zeech', 'Biology', 1, 'password'),
('FinalTest', 'FinalTest@g.fmarion.edu', 'Brian', 'boom', 'Chemistry', 1, 'password'),
('TeacherTest3215', 'TeacherTest3215@g.fmarion.edu', 'Teacher', 'Test', 'Biology', 1, 'password');

-- --------------------------------------------------------

--
-- Table structure for table `teacher_chathistory`
--

CREATE TABLE `teacher_chathistory` (
  `toID` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `FromID` varchar(50) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teacher_chathistory`
--

INSERT INTO `teacher_chathistory` (`toID`, `email`, `name`, `FromID`, `date`) VALUES
('cwilliams2638', 'cwilliams2638@g.fmarion.edu', 'Teacher Test', 'TeacherTest3215', '2017-03-23'),
('cwilliams2638', 'cwilliams2638@g.fmarion.edu', 'Teacher Test', 'TeacherTest3215', '2017-03-26');

-- --------------------------------------------------------

--
-- Table structure for table `tutor`
--

CREATE TABLE `tutor` (
  `studentID` varchar(25) NOT NULL,
  `courseNumber` varchar(4) NOT NULL,
  `departID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tutor`
--

INSERT INTO `tutor` (`studentID`, `courseNumber`, `departID`) VALUES
('dleon6779', '207', 1001),
('cwilliams2638', '190', 1004),
('cwilliams2638', '401', 1004),
('cwilliams2638', '132', 1008),
('cwilliams2638', '201', 1008),
('TestTutor4525', '201', 1009);

-- --------------------------------------------------------

--
-- Table structure for table `tutor_chathistory`
--

CREATE TABLE `tutor_chathistory` (
  `studentID` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `classname` varchar(50) NOT NULL,
  `FromID` varchar(50) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tutor_chathistory`
--

INSERT INTO `tutor_chathistory` (`studentID`, `email`, `name`, `classname`, `FromID`, `date`) VALUES
('cwilliams2638', 'cwilliams2638@g.fmarion.edu', 'Dylan Leon', 'MATH132', 'DLeon4743', '2017-03-26'),
('cwilliams2638', 'cwilliams2638@g.fmarion.edu', 'Dylan Leon', 'MATH132', 'dleon6779', '2017-03-26'),
('cwilliams2638', 'cwilliams2638@g.fmarion.edu', 'Charlton Williams', 'Teacher', 'TeacherTest3215', '2017-03-23'),
('cwilliams2638', 'cwilliams2638@g.fmarion.edu', 'Charlton Williams', 'Teacher', 'TeacherTest3215', '2017-03-26');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `firstName` varchar(15) NOT NULL,
  `lastName` varchar(15) NOT NULL,
  `major` varchar(30) NOT NULL,
  `course` varchar(50) NOT NULL,
  `registered` tinyint(1) NOT NULL,
  `tutor` tinyint(1) NOT NULL,
  `password` varchar(25) NOT NULL,
  `permission` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`departID`,`courseNumber`,`sectionNumber`),
  ADD KEY `teacherID` (`teacherID`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`departID`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentID`);

--
-- Indexes for table `student_chathistory`
--
ALTER TABLE `student_chathistory`
  ADD PRIMARY KEY (`studentID`,`classname`,`FromID`,`date`),
  ADD KEY `FK_StudentChat_studentIDFrom` (`FromID`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`teacherID`);

--
-- Indexes for table `teacher_chathistory`
--
ALTER TABLE `teacher_chathistory`
  ADD PRIMARY KEY (`toID`,`FromID`,`date`),
  ADD KEY `FK_Teacher_FromID` (`FromID`);

--
-- Indexes for table `tutor`
--
ALTER TABLE `tutor`
  ADD PRIMARY KEY (`studentID`,`courseNumber`),
  ADD KEY `departID` (`departID`);

--
-- Indexes for table `tutor_chathistory`
--
ALTER TABLE `tutor_chathistory`
  ADD PRIMARY KEY (`studentID`,`classname`,`FromID`,`date`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `course_ibfk_1` FOREIGN KEY (`departID`) REFERENCES `department` (`departID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `course_ibfk_2` FOREIGN KEY (`teacherID`) REFERENCES `teacher` (`teacherID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `student_chathistory`
--
ALTER TABLE `student_chathistory`
  ADD CONSTRAINT `FK_StudentChat_studentIDFrom` FOREIGN KEY (`FromID`) REFERENCES `student` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_StudentChat_studentIDStudentID` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `teacher_chathistory`
--
ALTER TABLE `teacher_chathistory`
  ADD CONSTRAINT `FK_Teacher_FromID` FOREIGN KEY (`FromID`) REFERENCES `teacher` (`teacherID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tutor`
--
ALTER TABLE `tutor`
  ADD CONSTRAINT `tutor_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tutor_ibfk_2` FOREIGN KEY (`departID`) REFERENCES `department` (`departID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tutor_chathistory`
--
ALTER TABLE `tutor_chathistory`
  ADD CONSTRAINT `FK_TutorChat_studentID` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
