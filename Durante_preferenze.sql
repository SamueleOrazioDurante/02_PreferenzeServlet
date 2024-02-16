-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Feb 16, 2024 alle 21:48
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Durante_preferenze`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `classi`
--

CREATE TABLE `classi` (
  `annoClasse` int(11) NOT NULL,
  `sezioneClasse` varchar(1) NOT NULL,
  `indirizzoClasse` varchar(100) NOT NULL,
  `idClasse` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `classi`
--

INSERT INTO `classi` (`annoClasse`, `sezioneClasse`, `indirizzoClasse`, `idClasse`) VALUES
(3, 'B', 'Meccanica', 1),
(5, 'B', 'Informatica', 2),
(1, 'A', 'Chimica', 3),
(4, 'C', 'Elettronica', 4);

-- --------------------------------------------------------

--
-- Struttura della tabella `preferenze`
--

CREATE TABLE `preferenze` (
  `matricola` int(11) NOT NULL,
  `idProfessore` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `preferenze`
--

INSERT INTO `preferenze` (`matricola`, `idProfessore`) VALUES
(2, 1),
(1, 2),
(3, 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `professori`
--

CREATE TABLE `professori` (
  `idProfessore` int(11) NOT NULL,
  `usernameProfessore` varchar(100) NOT NULL,
  `nomeProfessore` varchar(100) NOT NULL,
  `cognomeProfessore` varchar(100) NOT NULL,
  `materia` varchar(100) NOT NULL,
  `idClasse` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `professori`
--

INSERT INTO `professori` (`idProfessore`, `usernameProfessore`, `nomeProfessore`, `cognomeProfessore`, `materia`, `idClasse`) VALUES
(1, 'LDorato', 'Luca', 'Dorato', 'TPSIT', 2),
(2, 'MGuerra', 'Marta', 'Guerra', 'Matematica', 3),
(3, 'PMontarella', 'Panama', 'Montarella', 'Italiano', 4),
(4, 'FGiulibbea', 'Federica', 'Giulibbea', 'GPOI', 4);

-- --------------------------------------------------------

--
-- Struttura della tabella `studenti`
--

CREATE TABLE `studenti` (
  `matricola` int(11) NOT NULL,
  `usernameStudente` varchar(100) NOT NULL,
  `nomeStudente` varchar(100) NOT NULL,
  `cognomeStudente` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `idClasse` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `studenti`
--

INSERT INTO `studenti` (`matricola`, `usernameStudente`, `nomeStudente`, `cognomeStudente`, `password`, `idClasse`) VALUES
(1, 'Magnetar', 'Mario', 'Rossi', 'MarioRossi', 3),
(2, 'ISRaiken', 'Samuele Orazio', 'Durante', 'Ciao123', 2),
(3, 'Aquarius', 'Marinella', 'Passoni', 'MPassoni', 4),
(4, 'Triangulum', 'Maria Teresa', 'Magnalbo', 'MTMagnalbo', 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `classi`
--
ALTER TABLE `classi`
  ADD PRIMARY KEY (`idClasse`);

--
-- Indici per le tabelle `preferenze`
--
ALTER TABLE `preferenze`
  ADD PRIMARY KEY (`matricola`),
  ADD KEY `idProfessore` (`idProfessore`);

--
-- Indici per le tabelle `professori`
--
ALTER TABLE `professori`
  ADD PRIMARY KEY (`idProfessore`),
  ADD KEY `idClasse` (`idClasse`);

--
-- Indici per le tabelle `studenti`
--
ALTER TABLE `studenti`
  ADD PRIMARY KEY (`matricola`),
  ADD KEY `idClasse` (`idClasse`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `classi`
--
ALTER TABLE `classi`
  MODIFY `idClasse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `professori`
--
ALTER TABLE `professori`
  MODIFY `idProfessore` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `studenti`
--
ALTER TABLE `studenti`
  MODIFY `matricola` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `preferenze`
--
ALTER TABLE `preferenze`
  ADD CONSTRAINT `preferenze_ibfk_1` FOREIGN KEY (`matricola`) REFERENCES `studenti` (`matricola`),
  ADD CONSTRAINT `preferenze_ibfk_2` FOREIGN KEY (`idProfessore`) REFERENCES `professori` (`idProfessore`);

--
-- Limiti per la tabella `professori`
--
ALTER TABLE `professori`
  ADD CONSTRAINT `professori_ibfk_1` FOREIGN KEY (`idClasse`) REFERENCES `classi` (`idClasse`);

--
-- Limiti per la tabella `studenti`
--
ALTER TABLE `studenti`
  ADD CONSTRAINT `studenti_ibfk_1` FOREIGN KEY (`idClasse`) REFERENCES `classi` (`idClasse`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
