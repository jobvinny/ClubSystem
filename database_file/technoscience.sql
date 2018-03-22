-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 22, 2018 at 03:37 PM
-- Server version: 10.1.29-MariaDB
-- PHP Version: 7.2.0

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
(12345678, 'techguy', '6668', 'c53253f25296a5c5c3c869c57ffc9b483b989184bec97a06da75f0dae08b429957c3b570d0c2b603f57e9df7fa73360d');

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
('vincent', 'eb3/17613/14', 'OS Installation', 200, 2, 100, 'tech', 500, 100, 'Thursday, 12 October 2017, 03:09:09.762 AM', '-696513712'),
('techguy', 'eb1', 'Blowing', 50, 5, 250, 'mercy', 300, 50, 'Thursday, 12 October 2017, 03:24:43.984 AM', '1421221339'),
('ereber', 'brbtr', 'Blowing[Inside]', 150, 4, 600, 'brrrtb', 600, 0, 'Thursday, 12 October 2017, 03:28:02.213 AM', '-1391438665'),
('th', 'nun', 'Blowing[Inside]', 150, 2, 300, 'unnu', 500, 200, 'Thursday, 12 October 2017, 03:30:56.874 AM', '-2102760368'),
('greg', 'rger', 'OS Installation', 200, 4, 800, 'erge', 2453, 1653, 'Thursday, 12 October 2017, 03:34:24.276 AM', '-470626656'),
('df', 'bfb', 'OS Installation', 200, 2, 400, 'jay', 500, 100, 'Thursday, 12 October 2017, 08:00:49.128 AM', '135990597'),
('java', 'java', 'Taking Setups', 200, 4, 800, 'java', 1000, 200, 'Sunday, 28 January 2018, 05:49:00.970 PM', '907598983'),
('', '', 'Network Configuration', 50, 3232, 161600, '', 3232232, 3070632, 'Thursday, 22 March 2018, 05:35:32.141 PM', '-758206242');

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
-- Indexes for table `userlogin`
--
ALTER TABLE `userlogin`
  ADD PRIMARY KEY (`NationalID`),
  ADD UNIQUE KEY `personalnumber` (`personalnumber`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
