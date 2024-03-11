package main

import (
	assign_delivery_partner "FullfilmentServiceGrpc/proto"
	"context"
	"log"
	"math"
	"net"

	pb "FullfilmentServiceGrpc/proto" // Update this import path
	"google.golang.org/grpc"
)

type Server struct {
	assign_delivery_partner.DeliveryServiceServer
}

func (s *Server) GetNearestDeliveryPartner(ctx context.Context, req *pb.DeliveryExecutiveRequest) (*pb.DeliveryPartnerResponse, error) {
	restaurantLocation := req.GetRestaurantLocation()
	var deliveryPartnerID int64 = -1
	var minDistance = math.MaxFloat64

	for _, partner := range req.GetDeliveryPartners() {
		distance := calculateDistance(restaurantLocation, partner.GetLocation())
		if distance < minDistance {
			minDistance = distance
			deliveryPartnerID = partner.GetId()
		}
	}

	return &pb.DeliveryPartnerResponse{Id: deliveryPartnerID}, nil
}

func calculateDistance(location1 *pb.Location, location2 *pb.Location) float64 {
	dx := location1.GetLatitude() - location2.GetLatitude()
	dy := location1.GetLongitude() - location2.GetLongitude()
	return math.Sqrt(dx*dx + dy*dy)
}

func main() {
	lis, err := net.Listen("tcp", ":9090")
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterDeliveryServiceServer(s, &Server{})
	log.Println("Server started at :50051")
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
