#  Part A : Implementing technical indicators and trading signals ####

# - For this part, update the Unilever.csv file to include the 
#   information of technical indicators and trading signals.

# _______________________________________________________________ ####

####    Task 1 : Technical Indicators (10%)                       ####

# - This document updates the unilever.csv file with technical 
#   indicators and trading signals.

if (file.exists("unilever_orignial.csv")==TRUE){
  unilever_old <- read.csv("unilever_orignial.csv", header = TRUE)[2]
  unilever <- unilever_old
  names = c("DCP")
  colnames(unilever) <- names
} else {
  unilever <- read.csv("Unilever.csv", header = FALSE)
  unilever_old <- unilever
  names = c("DCP")
  colnames(unilever) <- names
}

View(unilever_old)


#View(unilever)

#### Task 1a - Exponential Moving Average (EMA)                   ####
rows <- length(unilever$DCP)

## --- Exponential Moving Average (EMA) ---
EMA <- function(L, t, preEMA){
  s <- 2 #smoothing
  P_t <- unilever$DCP[t]
  preEMA <- as.numeric(preEMA)
  #print(paste("P_t: ", P_t, "preEMA:" , preEMA))
  EMALt <- (P_t * (s/(1+L)))+(preEMA*(1-(s/(1+L))))
  return(round(EMALt,2))
  }

## --- Simple Moving Average (SMA) ---
SMA <- function(L,t){
  #print(paste('L:',L,' t:',t))
  sum1 = 0
  for(i in 1:L){
    sum1 = sum1 + unilever$DCP[t-i]
    sum1 = as.numeric(sum1)
    if(i==L){
      sum1 <- (sum1/L)
    }
  }
  #print(paste("sum1: ", sum1))
  return(round(sum1, 2))
}

## --- Calculate all EMAs ---
calcEMA <- function(L){
  EMA <- data.frame(data = 1:rows)
  for(t in 1:rows){
    if (t > L+1){
      EMA$data[t] <- EMA(L, t, preEMA)
      preEMA <- EMA$data[t]
    }
    else if(t == L+1){
      EMA$data[t] <- SMA(L,t)
      preEMA <- EMA$data[t]
    }
    else{
      EMA$data[t] <- "N/A"
    }
  }
  return(EMA$data)
}

# --- Calculate 12-day EMA and 16-day EMA ---
unilever$EMA12 <- calcEMA(12)
unilever$EMA26 <- calcEMA(26)

#_____________________________________________________________________

#### Task 1b - Calculate the 24-day trade break out rule (TBR)    ####

TBR <- function(L, t){
  P_t <- unilever$DCP[t]
  subset <- data.frame(n=1:L)
  for (i in 1:L){
    subset$n[i] <- unilever$DCP[t-i]
  }
  max <- max(subset$n)
  TBR_t <- (P_t - max)/(max)
  return(round(TBR_t, 2))
}

calcTBR <- function(L){
  TBR <- data.frame(data = 1:rows)
  for(t in 1:rows){
    if (t > L){
      TBR$data[t] <- TBR(L, t)
    }
    else{
      TBR$data[t] <- "N/A"
    }
  }
  return(TBR$data)
}

unilever$TBR24 <- calcTBR(24)

#_____________________________________________________________________

#### Task 1c - Calculate the 29-day volatility (VOL)              ####

Vol <- function(L,t){
  subset <- data.frame(n=1:L)
  for (i in 1:L){
    subset$n[i] <- unilever$DCP[t-i]
  }
  priceRange <- subset$n
  vol <- sd(subset$n)/SMA(L,t)
  return(round(vol, 3))
}

calcVol <- function(L){
  Vol <- data.frame(data = 1:rows)
  for(t in 1:rows){
    if (t > L){
      Vol$data[t] <- Vol(L, t)
    }
    else{
      Vol$data[t] <- "N/A"
    }
  }
  return(Vol$data)
}

unilever$Vol29 <- calcVol(29)

#_____________________________________________________________________

#### Task 1d - 25-Day Momentum (MOM)                               ####

MOM <- function(x, t){
  mom <- unilever$DCP[t] - unilever$DCP[t-x]
  return(round(mom, 2))
  }

calcMOM <- function(x){
  MOM <- data.frame(data = 1:rows)
  for(t in 1:rows){
    if (t > x){
      MOM$data[t] <- MOM(x, t)
    }
    else{
      MOM$data[t] <- "N/A"
    }
  }
  return(MOM$data)
}

unilever$MOM25 <- calcMOM(25)

# _______________________________________________________________ ####

####    Task 2 : Trading signals (10%)                            ####

#### Task 2a - Use EMA indicators to generate buy/sell signals    ####
action <- function(A, B){
  print(paste(A, " ? ", B))
  if      (A > B)  {return(1)} # if A > B => buy
  else if (A < B)  {return(2)} # if A < B => sell
  else if (A == B) {return(0)} # if A = B => hold
  else             {return("error")} # else     => error
}

unilever$EMA_Action <- 3

# --- Generate buy and sell signals for EMA indicators ---
for(i in 1:rows){
  if(unilever$EMA12[i] == "N/A" || unilever$EMA26[i] == "N/A"){
    unilever$EMA_Action[i] <- "N/A"
  }
  else{
    EMA12_i <- as.numeric(unilever$EMA12[i])
    EMA26_i <- as.numeric(unilever$EMA26[i])
    unilever$EMA_Action[i] <- action(EMA12_i, EMA26_i)
  }
}

#### Task 2b - Use TBR indicator to generate signals              ####
unilever$TBR_Action <- 3

for(i in 1:rows){
  if (unilever$TBR24[i] == "N/A"){
    unilever$TBR_Action[i] <- "N/A"
  }
  else {
    TBR24_i <- as.numeric(unilever$TBR24[i])
    unilever$TBR_Action[i] <- action(-0.02, TBR24_i)
  }
}

#### Task 2c - Use VOL indicator to generate signals              ####
unilever$VOL_Action <- 3

for(i in 1:rows){
  if (unilever$Vol29[i] == "N/A"){
    unilever$VOL_Action[i] <- "N/A"
  }
  else{
    Vol29_i <- as.numeric(unilever$VOL_Action[i])
    unilever$VOL_Action[i] <- action(Vol29_i, 0.02)
  }
}

#### Task 2d - Use MOM indicator to generate signals              ####
unilever$MOM_Action <- 3

for(i in 1:rows){
  if (unilever$MOM25[i] == "N/A") {
    unilever$MOM_Action[i] <- "N/A"
  }
  else {
    MOM_i <- as.numeric(unilever$MOM25[i])
    unilever$MOM_Action[i] <- action(MOM_i, 0)
  }
}

#_____________________________________________________________________

# --- Set Names ---
names <- c("Daily Closing Price", "12-Day Exponential Moving Average",
           "26-Day Exponential Moving Average", 
           "24-Day Trade Break Out Rule", "29-Day Volatility",
           "25-day Momentum", "EMA Action", "TBR Action", 
           "VOL Action", "MOM Action")
colnames(unilever) <- names

write.csv(unilever_old$V1, 'unilever_orignial.csv')
write.csv(unilever, 'unilever.csv')

rm(unilever)
rm(unilever_old)
