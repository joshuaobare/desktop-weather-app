-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 27, 2023 at 10:52 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `desktopweatherapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `api_calls`
--

CREATE TABLE `api_calls` (
  `callID` int(11) NOT NULL,
  `time` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `api_calls`
--

INSERT INTO `api_calls` (`callID`, `time`) VALUES
(1, '2023-03-18 14:41:28'),
(2, '2023-03-18 14:41:30'),
(3, '2023-03-18 15:02:34'),
(4, '2023-03-18 15:02:36'),
(5, '2023-03-18 15:02:44'),
(6, '2023-03-18 15:02:45'),
(7, '2023-03-18 15:02:53'),
(8, '2023-03-18 15:02:55'),
(9, '2023-03-18 15:03:03'),
(10, '2023-03-18 15:03:04'),
(11, '2023-03-18 15:35:38'),
(12, '2023-03-18 15:35:40'),
(13, '2023-03-18 15:37:27'),
(14, '2023-03-18 15:37:29'),
(15, '2023-03-18 15:41:21'),
(16, '2023-03-18 15:41:23'),
(17, '2023-03-18 15:46:00'),
(18, '2023-03-18 15:46:00'),
(19, '2023-03-18 15:47:05'),
(20, '2023-03-18 15:47:07'),
(21, '2023-03-18 15:47:14'),
(22, '2023-03-18 15:47:15'),
(23, '2023-03-18 15:47:29'),
(24, '2023-03-18 15:47:29'),
(25, '2023-03-18 15:47:39'),
(26, '2023-03-18 15:47:39'),
(27, '2023-03-18 15:48:00'),
(28, '2023-03-18 15:48:02'),
(29, '2023-03-18 15:52:18'),
(30, '2023-03-18 15:52:19'),
(31, '2023-03-18 15:55:33'),
(32, '2023-03-18 15:55:35'),
(33, '2023-03-23 09:23:39'),
(34, '2023-03-23 09:23:42'),
(35, '2023-03-23 09:29:27'),
(36, '2023-03-23 09:29:29'),
(37, '2023-03-23 09:50:00'),
(38, '2023-03-23 09:50:02'),
(39, '2023-03-23 09:50:36'),
(40, '2023-03-23 09:50:38'),
(41, '2023-03-23 09:51:23'),
(42, '2023-03-23 09:51:25'),
(43, '2023-03-23 09:51:59'),
(44, '2023-03-23 09:52:01'),
(45, '2023-03-23 09:52:24'),
(46, '2023-03-23 09:52:26'),
(47, '2023-03-23 09:54:47'),
(48, '2023-03-23 09:54:49'),
(49, '2023-03-23 09:55:55'),
(50, '2023-03-23 09:55:57'),
(51, '2023-03-23 10:10:48'),
(52, '2023-03-23 10:10:49'),
(53, '2023-03-23 10:34:15'),
(54, '2023-03-23 10:34:17'),
(55, '2023-03-23 10:37:26'),
(56, '2023-03-23 10:37:28'),
(57, '2023-03-23 10:37:48'),
(58, '2023-03-23 10:37:49'),
(59, '2023-03-23 10:38:48'),
(60, '2023-03-23 10:38:50'),
(61, '2023-03-23 10:39:38'),
(62, '2023-03-23 10:39:40'),
(63, '2023-03-23 10:40:58'),
(64, '2023-03-23 10:41:00'),
(65, '2023-03-23 10:41:19'),
(66, '2023-03-23 10:41:21'),
(67, '2023-03-23 18:54:37'),
(68, '2023-03-23 18:54:40'),
(69, '2023-03-23 19:02:02'),
(70, '2023-03-23 19:02:04'),
(71, '2023-03-23 19:02:58'),
(72, '2023-03-23 19:03:00'),
(73, '2023-03-23 19:08:00'),
(74, '2023-03-23 19:08:02'),
(75, '2023-03-23 19:36:04'),
(76, '2023-03-23 19:36:05'),
(77, '2023-03-23 19:41:35'),
(78, '2023-03-23 19:41:37'),
(79, '2023-03-23 19:43:49'),
(80, '2023-03-23 19:43:51'),
(81, '2023-03-23 19:44:44'),
(82, '2023-03-23 19:44:45'),
(83, '2023-03-23 19:55:57'),
(84, '2023-03-23 19:55:59'),
(85, '2023-03-23 20:09:24'),
(86, '2023-03-23 20:09:26'),
(87, '2023-03-23 20:19:06'),
(88, '2023-03-23 20:19:08'),
(89, '2023-03-23 20:19:58'),
(90, '2023-03-23 20:20:00'),
(91, '2023-03-23 20:20:38'),
(92, '2023-03-23 20:20:40'),
(93, '2023-03-23 20:55:32'),
(94, '2023-03-23 20:55:34'),
(95, '2023-03-23 20:58:38'),
(96, '2023-03-23 20:58:40'),
(97, '2023-03-23 20:59:33'),
(98, '2023-03-23 20:59:35'),
(99, '2023-03-23 21:00:24'),
(100, '2023-03-23 21:00:26'),
(101, '2023-03-23 21:02:49'),
(102, '2023-03-23 21:02:51'),
(103, '2023-03-23 21:05:28'),
(104, '2023-03-23 21:05:30'),
(105, '2023-03-23 21:09:41'),
(106, '2023-03-23 21:09:43'),
(107, '2023-03-23 21:12:41'),
(108, '2023-03-23 21:12:42'),
(109, '2023-03-23 21:13:50'),
(110, '2023-03-23 21:13:52'),
(111, '2023-03-23 21:17:34'),
(112, '2023-03-23 21:17:36'),
(113, '2023-03-23 21:25:51'),
(114, '2023-03-23 21:25:52'),
(115, '2023-03-23 21:26:19'),
(116, '2023-03-23 21:26:21'),
(117, '2023-03-23 21:34:32'),
(118, '2023-03-23 21:34:34'),
(119, '2023-03-23 21:35:42'),
(120, '2023-03-23 21:35:44'),
(121, '2023-03-23 21:38:47'),
(122, '2023-03-23 21:38:49'),
(123, '2023-03-23 21:40:23'),
(124, '2023-03-23 21:40:26'),
(125, '2023-03-23 21:41:15'),
(126, '2023-03-23 21:41:16'),
(127, '2023-03-23 21:42:53'),
(128, '2023-03-23 21:42:55'),
(129, '2023-03-25 17:52:12'),
(130, '2023-03-25 17:52:14'),
(131, '2023-03-25 18:03:59'),
(132, '2023-03-25 18:04:01'),
(133, '2023-03-25 18:06:25'),
(134, '2023-03-25 18:06:27'),
(135, '2023-03-25 18:09:31'),
(136, '2023-03-25 18:09:33'),
(137, '2023-03-25 18:10:08'),
(138, '2023-03-25 18:10:10'),
(139, '2023-03-25 18:10:43'),
(140, '2023-03-25 18:10:45'),
(141, '2023-03-25 18:11:07'),
(142, '2023-03-25 18:11:08'),
(143, '2023-03-25 18:16:35'),
(144, '2023-03-25 18:16:37'),
(145, '2023-03-25 18:17:36'),
(146, '2023-03-25 18:17:38'),
(147, '2023-03-25 18:20:53'),
(148, '2023-03-25 18:20:55'),
(149, '2023-03-25 18:23:43'),
(150, '2023-03-25 18:23:45'),
(151, '2023-03-25 18:24:37'),
(152, '2023-03-25 18:24:39'),
(153, '2023-03-25 18:26:35'),
(154, '2023-03-25 18:26:37'),
(155, '2023-03-25 18:48:06'),
(156, '2023-03-25 18:48:08'),
(157, '2023-03-25 18:52:24'),
(158, '2023-03-25 18:52:26'),
(159, '2023-03-25 19:04:09'),
(160, '2023-03-25 19:04:11'),
(161, '2023-03-25 19:04:36'),
(162, '2023-03-25 19:04:38'),
(163, '2023-03-25 19:06:15'),
(164, '2023-03-25 19:06:17'),
(165, '2023-03-25 19:32:23'),
(166, '2023-03-25 19:32:25'),
(167, '2023-03-25 19:35:23'),
(168, '2023-03-25 19:35:25'),
(169, '2023-03-25 19:50:04'),
(170, '2023-03-25 19:50:05'),
(171, '2023-03-25 19:50:20'),
(172, '2023-03-25 19:50:21'),
(173, '2023-03-25 19:50:39'),
(174, '2023-03-25 19:50:39'),
(175, '2023-03-25 19:55:03'),
(176, '2023-03-25 19:55:05'),
(177, '2023-03-25 19:55:17'),
(178, '2023-03-25 19:55:17'),
(179, '2023-03-25 19:55:31'),
(180, '2023-03-25 19:55:32'),
(181, '2023-03-25 19:55:37'),
(182, '2023-03-25 19:55:38'),
(183, '2023-03-25 21:07:47'),
(184, '2023-03-25 21:07:49'),
(185, '2023-03-25 21:14:40'),
(186, '2023-03-25 21:14:42'),
(187, '2023-03-25 21:15:15'),
(188, '2023-03-25 21:15:17'),
(189, '2023-03-25 21:16:16'),
(190, '2023-03-25 21:16:18'),
(191, '2023-03-25 21:17:58'),
(192, '2023-03-25 21:17:59'),
(193, '2023-03-25 21:19:31'),
(194, '2023-03-25 21:19:33'),
(195, '2023-03-25 21:21:24'),
(196, '2023-03-25 21:21:26'),
(197, '2023-03-25 21:22:29'),
(198, '2023-03-25 21:22:31'),
(199, '2023-03-25 21:23:01'),
(200, '2023-03-25 21:23:03'),
(201, '2023-03-25 21:23:41'),
(202, '2023-03-25 21:23:42'),
(203, '2023-03-25 21:24:33'),
(204, '2023-03-25 21:24:35'),
(205, '2023-03-25 21:27:12'),
(206, '2023-03-25 21:27:14'),
(207, '2023-03-25 21:29:13'),
(208, '2023-03-25 21:29:14'),
(209, '2023-03-25 21:36:19'),
(210, '2023-03-25 21:36:21'),
(211, '2023-03-25 21:39:37'),
(212, '2023-03-25 21:39:39'),
(213, '2023-03-25 21:40:59'),
(214, '2023-03-25 21:41:01'),
(215, '2023-03-25 21:41:21'),
(216, '2023-03-25 21:41:22'),
(217, '2023-03-25 21:42:39'),
(218, '2023-03-25 21:42:41');

-- --------------------------------------------------------

--
-- Table structure for table `licenses`
--

CREATE TABLE `licenses` (
  `licenseID` varchar(4) NOT NULL,
  `isAllocated` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `licenses`
--

INSERT INTO `licenses` (`licenseID`, `isAllocated`) VALUES
('L001', 0),
('L002', 1),
('L003', 0),
('L004', 0),
('L005', 0),
('L006', 0),
('L007', 0),
('L008', 0);

-- --------------------------------------------------------

--
-- Table structure for table `license_allocation`
--

CREATE TABLE `license_allocation` (
  `allocationID` int(11) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `licenseID` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `license_allocation`
--

INSERT INTO `license_allocation` (`allocationID`, `userID`, `licenseID`) VALUES
(1, 2, 'L002');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(70) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `signUpDate` datetime DEFAULT current_timestamp(),
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `name`, `email`, `isAdmin`, `signUpDate`, `password`) VALUES
(1, 'admin', 'admin@gmail.com', 1, '2023-02-01 17:30:58', 'test'),
(2, 'Jack', 'jack@gmail.com', 0, '2023-03-09 17:48:41', 'test'),
(3, 'Jill', 'jill@gmail.com', 0, '2023-03-23 21:25:08', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `weather_searches`
--

CREATE TABLE `weather_searches` (
  `searchID` int(11) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `location` varchar(20) DEFAULT NULL,
  `location_id` varchar(30) DEFAULT NULL,
  `weather_description` varchar(50) DEFAULT NULL,
  `temperature` varchar(10) DEFAULT NULL,
  `wind_speed` varchar(10) DEFAULT NULL,
  `time` datetime DEFAULT current_timestamp(),
  `feels_like` varchar(10) DEFAULT NULL,
  `humidity` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `weather_searches`
--

INSERT INTO `weather_searches` (`searchID`, `userID`, `location`, `location_id`, `weather_description`, `temperature`, `wind_speed`, `time`, `feels_like`, `humidity`) VALUES
(1, 1, 'Nairobi', '184745.0', 'light rain', '293.96', '4.12', '2023-03-25 19:55:18', '293.87', '68.0'),
(2, 1, 'Kigali', '202061.0', 'light rain', '293.86', '3.6', '2023-03-25 19:55:32', '293.66', '64.0'),
(3, 1, 'Nairobi', '184745.0', 'light rain', '293.96', '4.12', '2023-03-25 19:55:38', '293.87', '68.0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `api_calls`
--
ALTER TABLE `api_calls`
  ADD PRIMARY KEY (`callID`);

--
-- Indexes for table `licenses`
--
ALTER TABLE `licenses`
  ADD PRIMARY KEY (`licenseID`);

--
-- Indexes for table `license_allocation`
--
ALTER TABLE `license_allocation`
  ADD PRIMARY KEY (`allocationID`),
  ADD KEY `licenseID` (`licenseID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `name` (`name`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `weather_searches`
--
ALTER TABLE `weather_searches`
  ADD PRIMARY KEY (`searchID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `api_calls`
--
ALTER TABLE `api_calls`
  MODIFY `callID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=219;

--
-- AUTO_INCREMENT for table `license_allocation`
--
ALTER TABLE `license_allocation`
  MODIFY `allocationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `weather_searches`
--
ALTER TABLE `weather_searches`
  MODIFY `searchID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `license_allocation`
--
ALTER TABLE `license_allocation`
  ADD CONSTRAINT `license_allocation_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  ADD CONSTRAINT `license_allocation_ibfk_2` FOREIGN KEY (`licenseID`) REFERENCES `licenses` (`licenseID`),
  ADD CONSTRAINT `license_allocation_ibfk_3` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
