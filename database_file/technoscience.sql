-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 02, 2017 at 05:57 PM
-- Server version: 10.1.22-MariaDB
-- PHP Version: 7.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `technoscience`
--

-- --------------------------------------------------------

--
-- Table structure for table `adminlogin`
--

CREATE TABLE `adminlogin` (
  `NationalID` int(8) NOT NULL,
  `username` varchar(100) NOT NULL,
  `personalnumber` varchar(100) NOT NULL,
  `password` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adminlogin`
--

INSERT INTO `adminlogin` (`NationalID`, `username`, `personalnumber`, `password`) VALUES
(31407936, 'techguy', '1234', '93f725a07423fe1c889f448b33d21f46');

-- --------------------------------------------------------

--
-- Table structure for table `servicetable`
--

CREATE TABLE `servicetable` (
  `sname` varchar(100) NOT NULL,
  `rnumber` varchar(20) NOT NULL,
  `stype` varchar(1000) NOT NULL,
  `cost` double NOT NULL,
  `Tquantity` double NOT NULL,
  `Tcost` double NOT NULL,
  `sprovider` varchar(100) NOT NULL,
  `CashPaid` double NOT NULL,
  `ChangePaid` double NOT NULL,
  `LastEdited` varchar(100) NOT NULL,
  `Invoice` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `servicetable`
--

INSERT INTO `servicetable` (`sname`, `rnumber`, `stype`, `cost`, `Tquantity`, `Tcost`, `sprovider`, `CashPaid`, `ChangePaid`, `LastEdited`, `Invoice`) VALUES
('TechGyal', 'EB1...', 'OS Installation', 200, 1, 200, 'php', 200, 0, 'Thursday, 19 October 2017, 12:34:37.393 AM', '-1355759297'),
('TechGuy', 'EB3/17613/14', 'Network Card', 150, 2, 300, 'Jarvis', 500, 200, 'Thursday, 19 October 2017, 12:33:15.004 AM', '1112030698'),
('rgr', 'rggr', 'Application Sofware', 100, 1, 100, 'rgrg', 1000, 900, 'Saturday, 28 October 2017, 04:35:50.331 PM', '-1137105660');

-- --------------------------------------------------------

--
-- Table structure for table `userlogin`
--

CREATE TABLE `userlogin` (
  `NationalID` int(8) NOT NULL,
  `username` varchar(100) NOT NULL,
  `personalnumber` varchar(100) NOT NULL,
  `password` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userlogin`
--

INSERT INTO `userlogin` (`NationalID`, `username`, `personalnumber`, `password`) VALUES
(31407936, 'techguy', 'javafx', '93f725a07423fe1c889f448b33d21f46');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adminlogin`
--
ALTER TABLE `adminlogin`
  ADD PRIMARY KEY (`NationalID`),
  ADD UNIQUE KEY `personalnumber` (`personalnumber`);

--
-- Indexes for table `servicetable`
--
ALTER TABLE `servicetable`
  ADD PRIMARY KEY (`rnumber`);

--
-- Indexes for table `userlogin`
--
ALTER TABLE `userlogin`
  ADD PRIMARY KEY (`NationalID`),
  ADD UNIQUE KEY `personalnumber` (`personalnumber`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
