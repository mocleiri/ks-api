#!/usr/bin/env ruby

# 
# == Synopsis
#
# Classes for creating/managing tsung xml elements
#
# Author:: Kyle Campos (mailto:kcampos@rsmart.com)
#

require 'rubygems'
require 'builder'

@@tsung_xml_written       = false
@@session_element         = {}
@@transaction_element     = {}

# Main Sessions class, most often called from creating Session object
# This will write the 'sessions' xml elements
class TsungSessions

  attr_accessor :config
  attr_reader   :xml, :log, :tsung_element, :sessions_element
  
  def initialize(config)
    config.log.debug_msg("TsungSessions-> entering initialize...")
    @config           = config
    if(!@@tsung_xml_written)
      config.log.debug_msg("TsungSessions-> entering write_xml_elements...")
      @@tsung_element      = config.xml_writer.xml.elements['tsung']
      @@sessions_element   = @@tsung_element.add_element('sessions')
      @@tsung_xml_written  = true
    end

    self.config.log.debug_msg("Created TsungSessions: #{self.to_s}")
  end
  
  def to_s
    "TsungSessions-> Config: #{@config} xml_writer: #{@xml_writer} log: #{@log} tsung_element: #{@tsung_element} sessions_elements: #{@sessions_element}"
  end
end


# Individual session object. Writes the '<session>' xml element
class Session < TsungSessions
  
  attr_accessor :name, :probability, :type
  attr_reader :session_element, :transactions
  
  def initialize(config, session_name, probability='100%', type='ts_http')
    config.log.debug_msg("Session-> entering initialize...")
    super(config)
    @name = session_name
    @probability = probability
    @type = type
    @@session_element[@name] = {} if(!@@session_element[@name])
    
    # Check if this specific session element has been written
    if(!@@session_element[@name][:written])
      config.log.debug_msg("Session-> entering write_xml_elements...")
      @@session_element[@name][:element] = @@sessions_element.add_element('session', {"name" => @name, "probability" => @probability, "type" => @type})
      @@session_element[@name][:written] = true
    end
    
    @transactions = []
    
    config.log.debug_msg("Created session: #{self.to_s}")
  end
  
  # Add a transaction object to this session object
  def add_transaction(txn_name)
    txn = Transaction.new(txn_name, @config, @name, @probability, @type)
    @transactions << txn
    return txn
  end
  
  def to_s
    "Session-> Name: #{@name} Probability: #{@probability} Type: #{@type} session_element: #{@session_element}"
  end
end

# Individual transaction with many requests. Writes '<transaction>' xml element
class Transaction < Session
  
  attr_accessor :txn_name
  attr_reader :transaction_element, :request
  
  def initialize(txn_name, config, session_name, probability='100%', type='ts_http')
    config.log.debug_msg("Transaction-> entering initialize...")
    super(config, session_name, probability, type)
    @txn_name    = txn_name
    @@transaction_element[@txn_name] = {} if(!@@transaction_element[@txn_name])
    
    # Check if transaction element has been written yet
    if(!@@transaction_element[@txn_name][:written])
      config.log.debug_msg("Transaction-> entering write_xml_elements...")
      @@transaction_element[@txn_name][:element] = @@session_element[session_name][:element].add_element('transaction', {"name" => @txn_name})
      @@transaction_element[@txn_name][:written] = true
    end
    
    config.log.debug_msg("Created Transaction: #{@txn_name}")
  end
  
  # Creates Requests object that contains all requests
  def add_requests
    @request = Requests.new(@txn_name, self.config, self.name, self.probability, self.type)
  end

  # Returns all requests contained in this transaction
  def requests
    @requests_obj.list
  end
  
end


# Object container for all requests inside the transaction
class Requests < Transaction
  
  attr_accessor :list
  attr_reader :xml_element
  
  def initialize(txn_name, config, session_name, probability='100%', type='ts_http')
    config.log.debug_msg("Requests-> entering initialize...")
    super(txn_name, config, session_name, probability, type)
    @list = [] # request list
    @xml_element = @@transaction_element[txn_name][:element]
    config.log.debug_msg("Created Request container")
  end
  
  # Add a request to the container
  #
  # Option: DEFAULT_VALUE
  # * 'method': 'GET'
  # * 'version': '1.1'
  def add(url, opts={})
    
    defaults = {
      "method" => 'GET',
      "version" => '1.1',
      "url" => url
      #:content_type => nil,
      #:contents => nil
    }
  
    opts = defaults.merge(opts)
    
    # Make sure requests begins with app context
    if(url !~ /^\/self.config.context/) 
      new_url = '/' + self.config.context
      new_url << '/' if(url !~ /^\//)
      url = new_url + url
      opts["url"] = url
    end
        
    # Building request string soley for list method
    req_str = "<http url='#{url}' version='#{opts[:version]}' method='#{opts[:method]}'"
    req_str << " content_type='#{opts[:content_type]}'" if(opts[:content_type])
    req_str << " contents='#{opts[:contents]}'" if(opts[:contents])
    req_str << ">"

    req = @xml_element.add_element('request')
    req.add_element('http', opts)
    
    @list << req_str
  end
  
  # Add thinktime
  #
  # Option: DEFAULT_VALUE
  # * 'random': 'true'
  def add_thinktime(value=5, opts={})
    
    defaults = {
      "random" => "true",
      "value" => value
    }
    
    opts = defaults.merge(opts)
     
     @xml_element.add_element('thinktime', opts)
  end
  
end


# Class meant to handle all output IO
class Writer

  attr_accessor :file_path
  
  def initialize(file_path)
    @file_path = file_path
  end
  
end

# XML Writer
class XmlWriter < Writer
  
  attr_reader :xml, :file
  
  def initialize(config)
    super(config.output)
    @file = File.open(file_path, 'w')
    @xml = config.xml_obj
  end
  
end

# Log writer
class LogWriter < Writer
  
  attr_reader :file
  
  def initialize(config)
    super(config.log_path)
    @file = File.open(config.log_path, 'w')
    @config = config
  end
  
  # Message sent to stdout as well as log file
  def info_msg(msg="")
    puts(msg)
    @file.puts(msg)
  end
  
  # Message sent to stdout and log file only if debug flag is on
  def debug_msg(msg="")
    if(@config.debug)
      puts("Debug: #{msg}")
      @file.puts("Debug: #{msg}")
    end
  end
  
end
