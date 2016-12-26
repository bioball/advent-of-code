module Day3 where

import Data.Map
import Prelude

main :: IO()
main = do
    input <- readFile "day_3_input.txt"
    putStr input

placePresents :: [String] -> Set 
