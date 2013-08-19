<?php
    
    include 'dataFetchingMethods.php';
    
    if(isset($_GET['action']) && !empty($_GET['action'])) 
    {
        $action = $_GET['action'];        
        switch ($action){
            case 'getCurrentLocation':
                echo getCurrentLocation();
                break;
            case 'getLocationByDate':                   
                echo getLocationByDate($_GET['date']);
                break;
            default:
                break;
        }
    }      
?>